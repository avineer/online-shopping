$(function() {
	// solving the active menu problem
	switch(menu) {
	
		case 'About Us':
			$('#about').addClass('active');
			break;
		case 'Contact Us':
			$('#contact').addClass('active');
			break;
		case 'All Products':
			$('#listProducts').addClass('active');
			break;
		case 'Manage Products':
			$('#manageProducts').addClass('active');
			break;				
		default:
			if (menu == "Home") break;
			$('#listProducts').addClass('active');
		    $('#a_'+menu).addClass('active');
			break;	
	
	}

	
	
	// Code for jQuery dataTable
	
	var $table = $('#productListTable');
	
	// execute the below code only where we have this table
	if($table.length) {
		//console.log('Inside the table!');
		
		var jsonUrl = '';
		if (window.categoryId == '') {
			jsonUrl = window.contextRoot + '/json/data/all/products';
		}
		else {
			jsonUrl = window.contextRoot + '/json/data/category/' + window.categoryId + '/products';
		}			
		
		$table.DataTable( {
			lengthMenu: [[3,5,10,-1], ['3 Records', '5 Records', '10 Records', 'ALL']],
			pageLength: 5,
			ajax: {
				url: jsonUrl,
				dataSrc: ''	
			},
			columns: [
				
						{
							// hidden column to display product image
							data : 'code',
							bSortable : false,
							mRender : function(data, type, row) {

								return '<img src="' + window.contextRoot
									+ '/resources/images/' + data
									+ '.jpg" class="dataTableImg"/>';

							}
						},
				
						{
							data: 'name'
						},
						{
							data: 'brand'
						},
						{
							data: 'unitPrice',
							mRender: function(data, type, row) {
								// Create our number formatter.
								var formatter = new Intl.NumberFormat('en-US', {
								  style: 'currency',
								  currency: 'USD',
								  minimumFractionDigits: 2,
								});
								
								return formatter.format(data)
								//return '&#36;' + data.toFixed(2)
							}
						},
						{
							data: 'quantity',
							mRender: function(data, type, row) {
								if (data < 1) {
									return ' <span style="color:red">Out of Stock!</span>';
								}
								return data;
							}
						},
						{
							// Hidden column to display icons 
							data: 'id',
							bSortable : false,
							mRender: function(data, type, row) {
							
									var str = '';
																		
									str += '<a href="'
										+ window.contextRoot
										+ '/show/'
										+ data
										+ '/product" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></a> &#160;';

									if (row.quantity < 1) {
										str += '<a href="javascript:void(0)" class="btn btn-success disabled"><span class="glyphicon glyphicon-shopping-cart"></span></a>';
									}
									else {
										str += '<a href="'
											+ window.contextRoot
											+ '/cart/add/'
											+ data
											+ '/product" class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart"></span></a>';										
									}
									
									return str;
							}
								
						}
					  ]
		});		
		
	}
	
	// Dismissing the alert after 3 seconds
	var $alert = $('.alert');
	if ($alert.length) {
		setTimeout(function() {
			$alert.fadeOut('slow');
		} ,  3000)
	}
	
	
	//----------------------
	
	
	//------------------
	

	// Code for jQuery dataTable for admin
	//-------------------------------------
	
	var $adminProductsTable = $('#adminProductsTable');
	
	// execute the below code only where we have this table
	if($adminProductsTable.length) {
		//console.log('Inside the table!');
		
		var	jsonUrl = window.contextRoot + '/json/data/admin/all/products';		
		
		$adminProductsTable.DataTable( {
			lengthMenu: [[10, 30, 50, -1], ['10 Records', '30 Records', '50 Records', 'ALL']],
			pageLength: 30,
			ajax: {
				url: jsonUrl,
				dataSrc: ''	
			},
			columns: [
				
						{
							data: 'id'
						},
				
						{
							// hidden column to display product image
							data : 'code',
							bSortable : false,
							mRender : function(data, type, row) {

								return '<img src="' + window.contextRoot
									+ '/resources/images/' + data
									+ '.jpg" class="adminDataTableImg"/>';

							}
						},				
						{
							data: 'name'
						},
						{
							data: 'brand'
						},
						{
							data: 'quantity',
							mRender: function(data, type, row) {
								if (data < 1) {
									return ' <span style="color:red">Out of Stock!</span>';
								}
								return data;
							}
						},
						{
							data: 'unitPrice',
							mRender: function(data, type, row) {
								// Create our number formatter.
								var formatter = new Intl.NumberFormat('en-US', {
								  style: 'currency',
								  currency: 'USD',
								  minimumFractionDigits: 2,
								});
								
								return formatter.format(data)
								//return '&#36;' + data.toFixed(2)
							}
						},						
						{
							data: 'active',
							bSortable: false,
							mRender: function(data, type, row) {
								var str = '';
								
								console.log('in active');
								if(data) {	
									str += '<label class="switch"> <input type="checkbox" value="'+row.id+'" checked="checked">  <div class="slider round"> </div></label>';
									
								} else {
									str += '<label class="switch"> <input type="checkbox" value="'+row.id+'">  <div class="slider round"> </div></label>';
								}
								
								return str;
							}		
						},
						{
							data: 'id',
							bSortable: false,
							mRender: function(data, type, row) {
								var str ='';
								
								str += '<a href="'+window.contextRoot+'/manage/'+data+'/product" class="btn btn-warning">';
								str += '<span class="glyphicon glyphicon-pencil"></span></a>';
												
								return str;
							}
						}
						
						
					  ],
					  
						initComplete: function () {
							var api = this.api();
							api.$('.switch input[type="checkbox"]').on('change' , function() {	
								var checkbox = $(this);
								var checked = checkbox.prop('checked');
								var dMsg = (checked) ? 'You want to activate the Product?' :
									'You want to deactivate the Product?';
								var value = checkbox.prop('value');
								
							    bootbox.confirm({
							    	size: 'medium',
							    	title: 'Product Activation/Deactivation',
							    	message: dMsg,
							    	callback: function (confirmed) {
								        if (confirmed) {
								        	var activationUrl = window.contextRoot + '/manage/product/' + value + '/activation';
								        	$.post(activationUrl, function(data) {
								        		bootbox.alert({
								        			size: 'medium',
								        			title: 'Information',
								        			message: data
								        		});
								        	})							        	
								        }
								        else {							        	
								        	checkbox.prop('checked', !checked);
								        }
							    	}
							    });																											
							});
								
						}
		});		
		
	}
	
	
	//-----------------------------	
	// Validation code for category
	
	var $categoryForm = $('#categoryForm')
	
	if($categoryForm.length){
		$categoryForm.validate({
			rules: {
				name: {
					required: true,
					minlength: 2
				},
				description: {
					required: true
				}		
			},
			
			messages: {
				name: {
					required: 'Please add the category name!',
					minlength: 'The category name should be 2 or more chareacters!'
				},
				description: {
					required: 'Please add the category description!'
				}
			},
			errorElement:'em',
			errorPlacement: function(error,element) {
				// add the class of help-block
				error.addClass('help-block');
				// add the error element after the input element
				error.insertAfter(element);
			}			
		});
		
	}
	
	//---------------
	
	
});