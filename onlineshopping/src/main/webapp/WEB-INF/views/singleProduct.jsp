<div class="container">

	<!-- Breadcrumb -->
	<div class="row">
	
		<div class="col-xs-12">
		
			<ol class="breadcrumb">
		
				<li><a href="${contextRoot}/home">Home</a></li>
				<li><a href="${contextRoot}/show/all/products">Products</a></li>
				<li class="active">${product.name}</li>						
			
			</ol>
		
		</div>
	
	
	
	
	</div>

	<div class="row">
		
		<!-- Display the product image -->
		<div class="col-xs-12 col-sm-4">
		
			<div class="tumbnail">
				<img src="${images}/${product.code}.jpg" class="img img-responsive"/>
			
			</div>
		
		</div>
	
		<!--  Display the product description -->
		<div class="col-xs-12 col-sm-4">
		
			<h3>${product.name}</h3>
			<hr/>
			
			<p>${product.description}</p>
			<hr/>
			
				<h4>Price: 
 	        		<strong>
          			<fmt:setLocale value = "en_US"/>
         			<fmt:formatNumber value = "${product.unitPrice}" type = "currency"/>
         			</strong>
         		</h4>
			<hr/>
			
			
			<c:choose>			
				<c:when test="${product.quantity < 1}">
					<h6> Qty. Available: <span style="color:red">Out of Stock!</span></h6>
				</c:when>
				<c:otherwise>
					<h6> Qty. Available: ${product.quantity}</h6>
				</c:otherwise>
			</c:choose>

			<security:authorize access="hasAuthority('USER')">
			<c:choose>			
				<c:when test="${product.quantity < 1}">
					<a href="javascript:void(0)" class="btn btn-success disabled">
				 		<span class="glyphicon glyphicon-shopping-cart"></span>Add to Cart</a>
				</c:when>
				<c:otherwise>
					<a href="${contextRoot}/cart/add/${product.id}/product" class="btn btn-success">
				 		<span class="glyphicon glyphicon-shopping-cart"></span>Add to Cart</a>
				</c:otherwise>
			</c:choose>
			</security:authorize>
			
			<security:authorize access="hasAuthority('ADMIN')">
				<a href="${contextRoot}/manage/${product.id}/product" class="btn btn-warning">
				<span class="glyphicon glyphicon-pencil"></span> Edit</a>
			</security:authorize>						
				 
			<a href="${contextRoot}/show/all/products" class="btn btn-success">Back</a>			
		
		</div>
			
	</div>
	
	
</div>