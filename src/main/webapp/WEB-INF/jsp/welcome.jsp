<%@ page import="javax.validation.constraints.Max" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.Modele.Establishment" %>
<%@ page import="lombok.extern.java.Log" %><%--
  Created by IntelliJ IDEA.
  User: nassi
  Date: 11/02/2020
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Welcome</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2 id="article_header" class="text-warning" align="center">Spring Mvc and MongoDb Example</h2>
    <div> </div>

    <!-- Div to add a new user to the mongo database -->
    <div id="add_new_user">
       <%-- <c:url var="addUrl" value="/user/add" /><a id="add" href="${addUrl}" class="btn btn-success">Add user</a>--%>
    </div>
    <div> </div>

    <!-- Table to display the user list from the mongo database -->
    <table >

        <tr >
            <th>Id</th><th>Name</th>
        </tr>


       <tr ><%
           List<Establishment> establishments = (List<Establishment>)request.getAttribute("test");
           System.out.println("list");
           for (Establishment est : establishments ){
       %>
           <td><%=
               est.getId()
              // System.out.println("done");
              // System.out.println(est.getId());
           %></td>
           <td ><%=
           est.getName()
                   //System.out.println(est.getName());
           %></td>

           <% }%>
       </tr>


    </table>
</div>
</body>
</html>
