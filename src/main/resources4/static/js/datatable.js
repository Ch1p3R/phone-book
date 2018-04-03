$(document).ready( function () {
	 var table = $('#employeesTable').DataTable({
		 "paging":   false,
		 "searching": false,
		 "ajax": "/phonebook",
			"order": [[ 0, "asc" ]],
			"columns": [
			   /*   { "mData": "id"},*/
		          { "mData": "name" },
				  { "mData": "lastName" },
				  { "mData": "email" },
				  { "mData": "mobilePhone" },
		/*		  { "mData": "active" }*/
			]
	 })
});