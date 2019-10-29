<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
  </head>
  <style>
  	tr td{
  		font-size:12px;
  	}
  	.tt{
  		background-color:rgba(192,192,192,0.8);
  	}
  </style>
  <body>
	<div class="divMain">
		<div class="divTitle">
			<span style="margin-left:150px;margin-right:280px;">商品信息</span>
			<span style="margin-left:40px;margin-right:38px;">金额</span>
			<span style="margin-left:50px;margin-right:40px;">订单状态</span>
			<span style="margin-left:50px;margin-right:50px;">操作</span>
		</div><br/>

		<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
			<c:forEach items="${pb.beanList}" var="order">
				<tr class="tt">
					<td width="320px">订单号:<a href="<c:url value='/order/load?&oid=${order.oid }'/>">${order.oid }</a></td>
					<td width="200px">下单时间:${order.ordertime }</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr style="padding-top:10px;padding-bottom:10px;">
					<td colspan="2">
						<c:forEach items="${order.orderItemList }" var="orderItem">
							<a class="link2" href="<c:url value='/book/load?bid=${orderItem.book.bid }'/>">
								<img border="0" width="70" src="<c:url value='/${orderItem.book.image_b }'/>"/>
							</a>
						</c:forEach>
					</td>
					<td width="115px">
						<span class="price_t">&yen;${order.total }</span>
					</td>
					<td width="142px">
						<c:choose>
							<c:when test="${order.status eq 1 }">(等待发货)</c:when>
							<c:when test="${order.status eq 2 }">(准备发货)</c:when>
							<c:when test="${order.status eq 3 }">(等待确认)</c:when>
							<c:when test="${order.status eq 4 }">(交易成功)</c:when>
							<c:when test="${order.status eq 5 }">(已取消)</c:when>
						</c:choose>
					</td>
					<td>
						<a href="<c:url value='/order/load?&oid=${order.oid }'/>">查看</a><br>
						<c:if test="${order.status eq 1 }">
							<a href="<c:url value='/order/paymentPre?&oid=${order.oid}'/>">支付</a><br>
							<a href="<c:url value='/order/load?&oid=${order.oid }&btn=cancel'/>">取消</a><br>
						</c:if>
						<c:if test="${order.status eq 3 }">
							<a href="<c:url value='/order/load?&oid=${order.oid }&btn=confirm'/>">确认收货</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<%@include file="/WEB-INF/jsp/jsps/pager/pager.jsp" %>
		
	</div>
  </body>
</html>

<!-- <ul>

  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>








  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
   <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>
    <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/jsps/book/desc.jsp'/>"><img src="<c:url value='/book_img/23254532-1_b.jpg'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;40.7</span>
		<span class="price_r">&yen;50.9</span>
		(<span class="price_s">6.9折</span>)
	</p>
	<p><a id="bookname" title="Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）" href="<c:url value='/jsps/book/desc.jsp'/>">Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）</a></p>
	<p><a href="<c:url value='/jsps/book/list.jsp'/>" name='P_zz' title='Craig Walls'>Craig Walls</a></p>
	<p class="publishing">
		<span>出 版 社：</span><a href="<c:url value='/jsps/book/list.jsp'/>">人民邮电出版社</a>
	</p>
	<p class="publishing_time"><span>出版时间：</span>2013-06-01</p>
  </div>
  </li>




</ul>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	
</div>
 -->

