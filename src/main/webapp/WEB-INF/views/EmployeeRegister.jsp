<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>    
<html>
<head>
<title>Insert title here</title>
<style type="text/css">
.errors {
	color: red;
}
</style>
</head>
<body>
<a href="?lang=en">ENGLISH</a>
<a href="?lang=hi">HINDI</a>
<a href="?lang=te">TELUGU</a>
<a href="?lang=kn">KANNADA</a>

<h2><spring:message code="title"/> </h2>
<form:form action="insert" method="post" modelAttribute="employee">
<pre>
<spring:message code="ename"/>     : <form:input path="empName"/>
<form:errors path="empName" cssClass="errors"/>
<spring:message code="epwd"/> : <form:password path="empPwd"/>
<form:errors path="empPwd" cssClass="errors"/>
<spring:message code="egen"/>   : <form:radiobutton path="empGen" value="Male"/> Male <form:radiobutton path="empGen" value="Female"/> Female
<form:errors path="empGen" cssClass="errors"/>
<spring:message code="eaddr"/>  : <form:textarea path="empAddr"/>
<form:errors path="empAddr" cssClass="errors"/>
<spring:message code="ecntry"/>  : <form:select path="empCntry">
			<form:option value="">-SELECT-</form:option>
			<form:option value="IND">IND</form:option>
			<form:option value="AUS">AUS</form:option>
			<form:option value="DNR">DNR</form:option>
		   </form:select>
<form:errors path="empCntry" cssClass="errors"/>		   
<spring:message code="elang"/>: 
	<form:checkbox path="empLang" value="ENG"/> ENG		   
	<form:checkbox path="empLang" value="HIN"/> HIN		   
	<form:checkbox path="empLang" value="TEL"/> TEL		   
	<form:checkbox path="empLang" value="TAM"/> TAM
<form:errors path="empLang" cssClass="errors"/>	
<input type="submit" value="Register"/>			   
</pre>
</form:form>
${message}
</body>
</html>

