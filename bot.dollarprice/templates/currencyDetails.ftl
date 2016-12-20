<html dir="rtl">
	<head>
		<meta charset="UTF-8">
		<style type="text/css">
			table.imagetable {
				font-family: verdana,arial,sans-serif;
				font-size:11px;
				color:#333333;
				border-width: 1px;
				border-color: #999999;
				border-collapse: collapse;
				width:500px;
			}
			table.imagetable th {
				background:#b5cfd2 url('cell-blue.jpg');
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #999999;
				font-family:Tahoma;
				font-size: 14px;
			}
			table.imagetable td {
				background:#dcddc0 url('cell-grey.jpg');
				border-width: 1px;
				padding: 8px;
				border-style: solid;
				border-color: #999999;
				font-family:Tahoma;
				font-size: 14px;
				text-align: right
			}
		</style>

	</head>
	<body>
		<div align="right">
			<div>
				<h1 style="font-family:Tahoma;font-weight: 450;font-size: 14px;">
					${price_label}					
				</h1>
			</div>
			<div style="font-family:Tahoma;font-weight: 350;font-size: 12px;">
				${latest_update_date_label} &nbsp; ${latest_update_date} 
			</div>			
		</div>
		<div>
			
			<div align="right" style="font-family:Tahoma;font-weight: 350;font-size: 13px;">${best_sell_string}</div>
			<div align="right" style="font-family:Tahoma;font-weight: 350;font-size: 13px;">${best_buy_string}</div>
			
			<br>
			
			<div dir="rtl">
				<table class="imagetable">
					<tr>
						<th>
							${bank_keyword_label}
						</th>
						<th>
							${sell_keyword_label}
						</th>
						<th>
							${buy_keyword_label}
						</th>						
					</tr>
					<#list banks as bank>											
						<tr>
							<td>
								${bank.bank.name_ar}
							</td>
							<td align="center">
								${bank.sell?string["00.00"]}								
							</td>
							<td align="center">
								${bank.buy?string["00.00"]}
							</td>							
						</tr>						
					</#list>
				</table>
			</div>				
		</div>
	</body>
</html>
							