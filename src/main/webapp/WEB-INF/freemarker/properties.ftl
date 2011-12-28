<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>MI-MPR-DIP - CTU FIT Admission - Application Properties</title>
    <style type="text/css">
        table.properties {
            text-align: center;
            font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
            font-weight: normal;
            font-size: 11px;
            color: #fff;
            width: 100%;
            background-color: #cc0066;
            border: 1px #000 solid;
            border-collapse: collapse;
            border-spacing: 0px;
        }

        table.properties td.rows {
            background-color: #ffffff;
            color: #000;
            padding: 4px;
            text-align: left;
            border: 1px #000 solid;
        }

        table.properties th.header {
            background-color: #cc0066;
            color: #fff;
            padding: 4px;
            text-align: left;
            border-bottom: 2px #fff solid;
            font-size: 14px;
            font-weight: bold;
        }

    </style>
</head>
<body>
<table class="properties">
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