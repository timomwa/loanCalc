<!--
 ! Excerpted from "Stripes: and Java Web Development is Fun Again",
 ! published by The Pragmatic Bookshelf.
 ! Copyrights apply to this code. It may not be used to create training material, 
 ! courses, books, articles, and the like. Contact us if you are in doubt.
 ! We make no guarantees that this code is fit for any purpose. 
 ! Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
-->
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<!-- (1) -->
<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
	title="Contact List">
	<s:layout-component name="body">
		<s:messages />
		<s:link beanclass="stripesbook.action.ContactFormActionBean">
			Create a New Contact
		</s:link>
		<d:table name="${actionBean.contacts}" id="contact" requestURI=""
			defaultsort="1">
			<d:column title="Last name" property="lastName" sortable="true" />
			<d:column title="First name" property="firstName" sortable="true" />
			<d:column title="Email" property="email" sortable="true" />
			<d:column title="Action">
				<s:link beanclass="stripesbook.action.ContactListActionBean"
					event="view">
					<s:param name="contactId" value="${contact.id}" />
          View
        </s:link> |
        
        <s:link beanclass="stripesbook.action.ContactFormActionBean">
<s:param name="contactId" value="${contact.id}"/>
Update
</s:link>

|        <s:link beanclass="stripesbook.action.ContactListActionBean"
					event="delete" onclick="return confirm('Delete ${contact}?');">
					<s:param name="contactId" value="${contact.id}" />
          Delete
        </s:link>
			</d:column>
		</d:table>
	</s:layout-component>
</s:layout-render>