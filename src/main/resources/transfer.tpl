<#include "default.html"> 
<#macro page_head> 
        <title>Transfer report</title>
         
</#macro>

<#macro navigation_body>
   <ul>
   <#list pairs as p>
      <li><a href="${p.a.name}-${p.b.name}.html">${p.a.name} ${p.b.name}</a></li>
   </#list>
   </ul>
   
</#macro> 
<#macro page_body> 
    <h1>Transfer report</h1>
        
       <#assign balance=0>
       
       

       <table id="table" class="table-striped span12 table-bordered">
          <thead>
             <tr>
              <tr>
                <th width="70px">Date</th>
                <th width="500px">Description</th>
                <th width="70px">Amount</th>
                <th width="40px"></th>
                <th width="70px">Converted</th>
                <th width="40px"></th>
                <th width="70px">Balance</th>
                <th width="80px"></th>
              </tr>
              </tr>
          </thead>          
          <tbody>
          <#list transactions as t>
             <#assign balance = root.nextBalance(t,balance)>                 
          <tr>
             <td>${t.date?string("yyyy.MM.dd")}</td>
             <td>${t.shortDescription}</td>
             <#if t.currency == root.currency>
               <td></td>
               <td></td>
             <#else>
               <td>${root.getAmount(t)}</td>
               <td>${t.currency}</td>
             </#if>             
             <td>${t.getNormalAmount(root)}</td>
             <td>${root.currency}</td>
             <td>${balance}</td>
             <td>${t.getFrom().getName()}</td>             
          </tr>
          </#list>
          </tbody>          
          </table>                
</#macro>
<@page_html/> 