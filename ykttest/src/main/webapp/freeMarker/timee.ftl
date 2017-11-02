<html>
<head></head>
<body>
<h1>user:</h1>
<p>userId:${user.id}</p>
<p>userName:${user.userName}</p>
<p>nickName:${user.nickName}</p>
<p>userType:${user.userType}</p>
<#list productList as x>
<li id="p-${x.id}">
    <a href="/show?id=${x.id}" class="link">
        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
        <h3>${x.title}</h3>
        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
        <#if user>1</#if>
        <p>${user.userType}</p>
        <p>${x.isBuy()}</p>
        <#if user && user.userType==1 && x.isSell()><span class="had"><b>已售出</b></span></#if>
    </a>
    <#if user && user.userType==1 && !x.isSell><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.id}">删除</span></#if>
</li>
</#list>
</body>
</html>