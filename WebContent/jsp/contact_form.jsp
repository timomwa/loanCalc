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
<%--
START:update
    <s:form beanclass="stripesbook.action.ContactFormActionBean">
END:update
--%>

    <s:form beanclass="stripesbook.action.ContactFormActionBean"><!-- (1) -->


      <div><s:hidden name="contact.id"/></div>

      <table class="form">

        <tr>
          <td>Email:</td>
          <td><s:text name="contact.email"/></td><!-- (2) -->
        </tr>
        <tr>
          <td>First name:</td>
          <td><s:text name="contact.firstName"/></td>
        </tr>
        <tr>
          <td>Last name:</td>
          <td><s:text name="contact.lastName"/></td>
        </tr>
        <tr>
          <td>Phone number:</td>
          <td><s:text name="contact.phoneNumber"/></td>
        </tr>
        <tr>
          <td>Birth date:</td>
          <td><s:text name="contact.birthDate"/></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <s:submit name="save" value="Save"/><!-- (3) -->
            <s:submit name="cancel" value="Cancel"/>
          </td>
        </tr>
      </table>
    </s:form>

  </s:layout-component>
</s:layout-render>
