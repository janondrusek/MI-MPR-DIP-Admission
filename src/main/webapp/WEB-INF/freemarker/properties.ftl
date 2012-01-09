<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>MI-MPR-DIP - CTU FIT Admission - Application Properties</title>
    <link href="../css/screen.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table>
    <tr>
        <th class="header" colspan="2">MI-MPR-DIP - CTU FIT Admission (${buildNumber}) - Properties</th>
    </tr>
<#list applicationProperties?keys as key>
    <tr>
        <td class="rows">${key}</td>
        <td class="rows">${applicationProperties[key]}</td>
    </tr>
</#list>
</table>
</body>
</html>