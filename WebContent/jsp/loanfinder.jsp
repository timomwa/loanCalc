<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
	title="Loan Amortization helper: Monthly Payment Calculator">
	<!-- (1) -->

	<s:layout-component name="calculate_area">
		<script type="text/javascript" src="js/examples.js"></script>

Enter Principle Amount: <input id="principle_amount"
			name="principle_amount" onkeyup="validatePrincipleAmt(this)"/>
		<br />
		<br />
Slide to select repayment period in months (1-60):<div id="months_slider"></div>
		<span id="months" style="float:left; margin-left: 400px; margin-top: -19px;">0 months</span>
		<br />
		<br />
		
		<h3>Below are some finance options from Kenyan banks.</h3><br/>
		<div id="institutions_all"> 
		<c:forEach items="${actionBean.institutions}" var="institution">
			<!--  d:column title="Name" property="name" sortable="true" />-->
			<div style="background-color: #CEE3F6" id="institution_${institution.id}"><b>${institution.name} </span></b></div>
			<ol id="finance_options_${institution.id}">
			<c:forEach items="${institution.finance_options}" var="financeOptions">
				<li  id="financeOptionLI_${financeOptions.id}" style="background-color: #EFF5FB">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>${financeOptions.name}&nbsp;&nbsp; <span style="float: right" id="financeOption_${financeOptions.id}">&nbsp;<b>KES 0.00</b></span></i><img width="15" style="float: right"  src="images/delete.png" onclick="deleteOption(${financeOptions.id})"/>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<img width="15"  style="float: right"  src="images/edit.png" onclick="editOption(${financeOptions.id})"/></li>
			</c:forEach>
			</ol>
			<br />
		</c:forEach>
		</div>



		<button onclick="calculateLoan()" style="float:right; ">Calculate Monthly Payment</button>
	</s:layout-component>


	<s:layout-component name="bank_area">
	
	
	<div id="my_form_parent">
	</div><br/><br/>
	<span id="addBank" style="float: left;"></span><span id="addFinanceOption" style="float: left; margin-left: 10px"></span>
	<br/><br/>
	<div id="my_form">
	</div>
	</s:layout-component>

</s:layout-render>
