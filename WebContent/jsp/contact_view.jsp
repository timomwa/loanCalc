<!--
 ! Excerpted from "Stripes: and Java Web Development is Fun Again",
 ! published by The Pragmatic Bookshelf.
 ! Copyrights apply to this code. It may not be used to create training material, 
 ! courses, books, articles, and the like. Contact us if you are in doubt.
 ! We make no guarantees that this code is fit for any purpose. 
 ! Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
-->
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<s:layout-render name="/WEB-INF/jsp/common/layout_main.jsp"
  title="Contact Information">
  <s:layout-component name="body">
  <s:messages/>
    <table class="view">
      <tr>
        <td class="label">First name:</td>
        <td class="value">${actionBean.contact.firstName}</td>
      </tr>
      <tr>
        <td class="label">Last name:</td>
        <td class="value">${actionBean.contact.lastName}</td>
      </tr>
      <tr>
        <td class="label">Email:</td>
        <td class="value">${actionBean.contact.email}</td>
      </tr>
      <tr>
        <td class="label">Phone number:</td>
        <td class="value">${actionBean.contact.phoneNumber}</td>
      </tr>
      <tr>
        <td class="label">Birth date:</td>
        <td class="value">${actionBean.contact.birthDate}</td>
      </tr>
    </table>
    <p>
      <s:link beanclass="stripesbook.action.ContactListActionBean" event="list" >
        Back to List
      </s:link>
    </p>
  </s:layout-component>
</s:layout-render>
