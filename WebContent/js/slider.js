/*!
 * Sample loan app. demo
 */

var institution_id = -1;
var finance_rate = -1;
var finance_option_id = -1;
var DEBUG_MODE = false;
Ext.BLANK_IMAGE_URL = 'tim/images/default/s.gif';
function isNumber(n) {
	  return !isNaN(parseFloat(n)) && isFinite(n);
	  
}
function validatePrincipleAmt(principle){
	var v = principle.value;
	
	while(!isNumber(v) && v.length>=1){
		if(v.length>=1){
			v = v.substring(0,(v.length-1));
		}else{
			v = '';
			break;
		}
	}
	principle.value = v;
}
 function editOption(option_id){
	 if(DEBUG_MODE)
	 alert('option_id = '+option_id);
	 
	 if(Ext.get('loanOpt_form_div'))
		 Ext.get('loanOpt_form_div').remove();
	 
	 finance_option_id = option_id;
	 
	 
	 
	 var simple = new Ext.FormPanel({
	        labelWidth: 75, // label settings here cascade unless overridden
	        id: 'addBankOptionForm',
	        frame:true, 
	        title: 'Please wait...',
	        bodyStyle:'padding:5px 5px 0',
	        width: 350,
	        defaults: {width: 230},
	        defaultType: 'textfield',
	        items: [{
	        	    id: 'financeOptionName',
	                fieldLabel: 'Finance Option Name',
	                name: 'financeOption',
	                allowBlank:false
	            	},
	            	{
		        	    id: 'financeOptionRate',
		                fieldLabel: 'Interest Rate',
		                name: 'financeOptionRate',
		                allowBlank:false,
		                validator: function(v){
		                	while(!isNumber(v) && v.length>=1){
		                		if(v.length>=1){
		                			v = v.substring(0,(v.length-1));
		                		}else{
		                			v = '';
		                			break;
		                		}
		                	}
		                	Ext.getDom('financeOptionRate').value = v;
		                }
		            }
	        ],

	        buttons: [{
	            text: 'Edit',
	            handler: function(){
	            	 var finance_option_name = Ext.getDom('financeOptionName').value;
	            	 var interest_rate = Ext.getDom('financeOptionRate').value;
	            	 saveFinanceOption(finance_option_name, interest_rate, institution_id);
	            	 
	            }
	        },
	        {
	        	text: 'Cancel',
	        	handler: function(){
	        		 if(Ext.get('loanOpt_form_div'))
	        			 Ext.get('loanOpt_form_div').remove();
	        		 
	        	}
	        }]
	    });
		
	 var bd = Ext.fly('my_form');
	  bd.createChild({tag: 'div', html: '', id: 'loanOpt_form_div'});
		
		
		
		 Ext.Ajax.request({
			   url: 'Loans.action?getFinancialOption',
			   success: function(response, opts) {
				  simple.render(document.getElementById('loanOpt_form_div'));
			      var obj = Ext.decode(response.responseText);
			      if(DEBUG_MODE)
			    	  console.log(obj);
			      var finance_option_id = obj.response.message.id;
			      var  institutionName =  obj.response.message.institutionName;
			      var name = obj.response.message.name;
			      var form_title = obj.response.message.institutionName;
			      simple.setTitle(form_title);
			      var  rate =  obj.response.message.rate;
			      institution_id =  obj.response.message.institution_id;
			      
			      Ext.getDom('financeOptionName').value = name;
			      Ext.getDom('financeOptionRate').value = rate;
			      institution_id = obj.response.message.institution_id;
			      finance_rate = rate;
			      finance_option_id = finance_option_id;
			     
			   },
			   params: {'financial_option_id' : option_id},
			   failure: function(response, opts) {
				   if(DEBUG_MODE)
					     console.log('server-side failure with status code ' + response.status);
			   }
			});
 }
 
 
 function deleteOption(option_id){
	 Ext.Msg.show({
		   title:'Delete?',
		   msg: 'Are you sure you want to delete this option?',
		   buttons: Ext.Msg.YESNOCANCEL,
		   fn: function(btn, text){
			   if('yes'==btn){
				   Ext.Ajax.request({
					   url: 'Loans.action?deleteFinancialOption',
					   success: function(response, opts) {
					      var li_to_remove =  'financeOptionLI_'+option_id;
					       if(Ext.get(li_to_remove))
								 Ext.get(li_to_remove).remove();
					       if(DEBUG_MODE)
							     console.log(response);
					   },
					   params: {'financial_option_id' : option_id},
					   failure: function(response, opts) {
						   if(DEBUG_MODE)
							     console.log('server-side failure with status code ' + response.status);
					   }
					});
			   }
			  
		   },
		   animEl: 'elId',
		   icon: Ext.MessageBox.QUESTION
		});
 }
 
 function calculateLoan(){
	 if(!Ext.slider.sliderVal){
		 Ext.slider.sliderVal = -1;
	 }
	 var principle = Ext.getDom('principle_amount');
	 
	 Ext.Ajax.request({
		   url: 'Loans.action?calculateRate',
		   success: function(response, opts) {
		      var obj = Ext.decode(response.responseText);
		      
		      for(var x =0; x<obj.premiums.length;x++){
		    	 var premium_field =  'financeOption_'+obj.premiums[x].finance_option_id;
		    	 Ext.fly(premium_field).update('<b>KES. '+obj.premiums[x].monthly_replayment+'</b>', false);
		      }
		      if(DEBUG_MODE)
		    	  console.dir(obj);
		   },
		   params: {'period_months' : Ext.slider.sliderVal, 'principle_amount' :  principle.value},
		   failure: function(response, opts) {
			   if(DEBUG_MODE)
				      console.log('server-side failure with status code ' + response.status);
		   }
		});
}
 
 
 function saveBank(bankName){
	 Ext.Ajax.request({
		   url: 'Loans.action?saveInstitution',
		   success: function(response, opts) {
			   var obj = Ext.decode(response.responseText);
			   if(DEBUG_MODE)
			     console.log(obj);
			   var institution_div = Ext.fly('institutions_all');
			   institution_div.createChild({tag: 'div',style: 'background-color: #CEE3F6',  html: '<b>'+bankName+'<b>', id: 'institution_'+obj.response.message.id});
			   if(Ext.get('addBankForm'))
					 Ext.get('addBankForm').remove();
		   },
		   params: {'institutionName' : bankName},
		   failure: function(response, opts) {
			   if(DEBUG_MODE)
				     console.log('server-side failure with status code ' + response.status);
		   }
		});
 }
 
 function showNewBankForm(){
	 Ext.fly('my_form').update('');
	 var simple = new Ext.FormPanel({
	        labelWidth: 75, // label settings here cascade unless overridden
	        id: 'addBankForm',
	        frame:true,
	        title: 'New Bank Form',
	        bodyStyle:'padding:5px 5px 0',
	        width: 350,
	        defaults: {width: 230},
	        defaultType: 'textfield',

	        items: [{
	        	    id: 'bankName',
	                fieldLabel: 'Bank Name',
	                name: 'first',
	                allowBlank:false
	            }
	        ],

	        buttons: [{
	            text: 'Save',
	            handler: function(){
	            	 var bankName = Ext.getDom('bankName');
	            	 saveBank(bankName.value);
	            	 
	            }
	        },{
	            text: 'Cancel',
	            handler: function(){
	            	 if(Ext.get('addBankForm'))
	            		 Ext.get('addBankForm').remove();
	            	 
	            }
	        }]
	    });
		
		simple.render(document.getElementById('my_form'));
 }
 
 

 
 function saveFinanceOption(finance_option_name, interest_rate, institution_id){
	 if(!institution_id)
		 institution_id = institution_id;
   
	 if(Ext.get('loanOpt_form_div'))
		 Ext.get('loanOpt_form_div').remove();
	 
	
	 
     
	 Ext.Ajax.request({
		   url: 'Loans.action?saveFinanceOption',
		   success: function(response, opts) {
			   var obj = Ext.decode(response.responseText);
			   if(DEBUG_MODE)
				     console.log(obj);
			   var title = 'Success';
			   var msg = 'Successfully saved Financial Option "'+finance_option_name+'" ';
			   Ext.MessageBox.alert(title,msg);
			   var bd = Ext.fly('finance_options_'+institution_id);
			
			   if(!bd){
				   var inst = Ext.fly('institution_'+institution_id);
				   inst.createChild({tag: 'ol', id:'finance_options_'+institution_id});
				   bd = Ext.fly('finance_options_'+institution_id);
			   }
			   var finance_option_id = obj.response.message.finance_option_id;
			   var li_to_remove =  'financeOptionLI_'+finance_option_id;
				 if(Ext.get(li_to_remove))
					 Ext.get(li_to_remove).remove();
			   bd.createChild({tag: 'li', style: 'background-color: #EFF5FB', html: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>'+finance_option_name+'&nbsp;&nbsp; <span style="float: right" id="financeOption_'+obj.response.message.id+'">&nbsp;<b>KES 0.00</b></span><img style="float: right"  width="15" src="images/delete.png" onclick="deleteOption('+obj.response.message.id+')"/>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img width="15"  style="float: right"  src="images/edit.png" onclick="editOption('+obj.response.message.id+')"/></i>', id: 'financeOptionLI_'+obj.response.message.id});
			   if(Ext.get('loanOpt_form_div'))
					 Ext.get('loanOpt_form_div').remove();
		   },
		   params: {'finance_option_id' : finance_option_id, 'finance_option_name' : finance_option_name, 'interest_rate' : interest_rate, 'institution_id' : institution_id },
		   failure: function(response, opts) {
			   if(DEBUG_MODE)
				     console.log('server-side failure with status code ' + response.status);
		   }
		});
 }
 
 function getComboDisplay(combo) {
	    var value = combo.getValue();
	    var valueField = combo.valueField;
	    var record;
	    combo.getStore().each(function(r){
	        if(r.data[valueField] == value){
	            record = r;
	            return false;
	        }
	    });

	    return record ? record.get(combo.displayField) : null;
}
 
 function loadFinanceOptionForm(institution){
	 if(Ext.get('loanOpt_form_div'))
		 Ext.get('loanOpt_form_div').remove();
	 var institution_id = institution.getValue();
	 var name = institution.getName();
	 var displayName = getComboDisplay(institution);
	 var simple = new Ext.FormPanel({
	        labelWidth: 75, // label settings here cascade unless overridden
	        id: 'addBankOptionForm',
	        frame:true,
	        title: 'New Bank Form ('+displayName+')',
	        bodyStyle:'padding:5px 5px 0',
	        width: 350,
	        defaults: {width: 230},
	        defaultType: 'textfield',
	        items: [{
	        	    id: 'financeOptionName',
	                fieldLabel: 'Finance Option Name',
	                name: 'financeOption',
	                allowBlank:false
	            	},
	            	{
		        	    id: 'financeOptionRate',
		                fieldLabel: 'Interest Rate',
		                name: 'financeOptionRate',
		                allowBlank:false,
		                validator: function(v){
		                	while(!isNumber(v) && v.length>=1){
		                		if(v.length>=1){
		                			v = v.substring(0,(v.length-1));
		                		}else{
		                			v = '';
		                			break;
		                		}
		                	}
		                	Ext.getDom('financeOptionRate').value = v;
		            	}
	            	}],

	        buttons: [{
	            text: 'Save',
	            handler: function(){
	            	 var finance_option_name = Ext.getDom('financeOptionName').value;
	            	 var interest_rate = Ext.getDom('financeOptionRate').value;
	            	 saveFinanceOption(finance_option_name, interest_rate, institution_id);
	            	 
	            }
	        },
	        {
	        	text: 'Cancel',
	        	handler: function(){
	        		 if(Ext.get('loanOpt_form_div'))
	        			 Ext.get('loanOpt_form_div').remove();
	        	}
	        }]
	    });
		
	 var bd = Ext.fly('my_form');
	 bd.createChild({tag: 'div', html: '', id: 'loanOpt_form_div'});
	 simple.render(document.getElementById('loanOpt_form_div'));
		
 }
 
function showFinanceOptionForm(){
	Ext.fly('my_form').update('');
	 var banks = new Ext.data.JsonStore({
	    	url: 'Loans.action?getAllInstitutions',
	    	root: 'bankList',
	    	storeId: 'banks',
	    	fields: [ {name:'id', type:'int'}, {name:'bankName', type:'string'}],
	    	autoLoad: true,
	    	listeners: {
	    		load: function(store, records, options){
	    			 if(DEBUG_MODE)
	    			     console.log(store.getAt(0));
	    		},
	    		loadexception: function(proxy, options, response, e){
	    			 if(DEBUG_MODE)
	    			     console.log('Error occurred: ' + e);
	    		}
	    
	    	}
	    });
	    
	
	var simple =  new Ext.form.ComboBox({
		    xtype: 'combo',
			store: banks,
	    	mode: 'remote',
	    	valueField: 'id',
	    	displayField: 'bankName',
	    	editable: false,
	    	forceSelection: true,
	    	triggerAction: 'all',
	    	emptyText: 'Select a bank to add rate for...',
	    	selectOnFocus: true,
			id: 'banks_list',
			fieldLabel: 'Bank',
			name: 'to',
			renderTo: "my_form",
			listeners: {
					'select': function(cmb, rec, idx) {
						 loadFinanceOptionForm(this);
					}
			}
     });
	 
	 simple.render(document.getElementById('my_form'));
}
Ext.onReady(function(){
	 
    var tip = new Ext.slider.Tip({
        getText: function(thumb){
        	Ext.slider.sliderVal =  thumb.value;
        	if(!thumb.value)
        		thumb.value = 0;
        	 Ext.fly('months').update('<b>'+thumb.value+' month'+(thumb.value>1 ? 's' : '' )+'</b>', false);
            return String.format('<b>{0} months</b>', thumb.value);
        }
    });

    var slider = new Ext.Slider({
        renderTo: 'months_slider',
        width: 214,
        increment: 1,
        minValue: 1,
        maxValue: 60,
        plugins: tip,
        id: 'period_id',
        title: 'Month selector',
        listeners : {
            change: function(slider, thumb, newValue, oldValue){
            	tip.getText(newValue);
            	 if(DEBUG_MODE)
    			     console.log('newValue['+slider.getValue()+'], oldValue['+oldValue+'] change slider changed!, newValue['+newValue+'] , newValue.value['+newValue.value+'] thumb = ['+ thumb+']');               
            }
        }
        	
        
    });
    
    
    
    Ext.QuickTips.init();

    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.fly('my_form_parent');

   bd.createChild({tag: 'h2', html: 'Admin Controls'});
    
   
   
    
    new Ext.Button({
		renderTo: 'addBank',
		text: 'Add Bank',
		handler: function(){
			showNewBankForm();
		}
	});
    new Ext.Button({
		renderTo: 'addFinanceOption',
		text: 'Add Finance Option',
		handler: function(){
			showFinanceOptionForm();
		}
	});
	
	 
    
   
});
