var dataTable;

function approve_action(item_id) {
	bootbox.confirm({
		message: "Are you sure you want to approve application?",
		buttons: {
			confirm: {
				label: "Yes",
				className: "btn-md btn-success",
			},
			cancel: {
				label: "No",
				className: "btn-md btn-danger",
			},
		},
		callback: function (result) {
			if (result) {
				$.ajax({
					type: "POST",
					url: `/api/leaves/${item_id}/APPROVED`,
					headers: {
						Authorization:
							"Bearer " + localStorage.getItem("accessToken"),
					},
					contentType: "application/json",
					success: function (res) {
						console.log(res);
						dataTable.ajax.reload();
					},
				});
			}
		},
	});
}

function reject_action(item_id) {
	bootbox.confirm({
		message: "Are you sure you want to reject application?",
		buttons: {
			confirm: {
				label: "Yes",
				className: "btn-success",
			},
			cancel: {
				label: "No",
				className: "btn-danger",
			},
		},
		callback: function (result) {
			if (result) {
				$.ajax({
					type: "POST",
					url: `/api/leaves/${item_id}/REJECTED`,
					headers: {
						Authorization:
							"Bearer " + localStorage.getItem("accessToken"),
					},
					contentType: "application/json",
					success: function (res) {
						console.log(res);
						dataTable.ajax.reload();
					},
				});
			}
		},
	});
}
function initDataTable() {
	dataTable = $("#leave-table").DataTable({
		ajax: {
			type: "GET",
			url: "http://localhost:5000/api/leaves",
			headers: {
				Authorization: "Bearer " + localStorage.getItem("accessToken"),
			},
			contentType: "application/json",
		},
		columns: [
			{ data: "leaveType", title: "Leave Type" },
			{ data: "startDate", title: "Start Date" },
			{ data: "endDate", title: "End Date" },
			{ data: "status", title: "Status" },
			{ data: "reason", title: "Reason" },
			{ data: "workDissemination", title: "Work Dissemination" },
			{ data: "contactDetails", title: "Contact Details" },
			{ data: "rejectReason", title: "Reject Reason" },
			{ data: "id", title: "Actions" },
		],
		responsive: true,
		columnDefs: [
			{
				targets: [0, 1, 2, 3],
				className: "all text-center align-middle",
			},
			{
				targets: [4, 5, 6, 7, 8],
				className: "align-middle",
			},
			{
				targets: -1,
				defaultContent: "-",
				searchable: false,
				orderable: false,
				className: "all text-center",
				render: function (data, type, row, meta) {
					return `<div style="display:block">
								<button onclick="approve_action('${row.id}')" type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#modal_delete" style="margin:3px">
									<i class="fa fa-check"></i>
										Approve
								</button>
								<button onclick="reject_action('${row.id}')" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal_delete" style="margin:3px">
									<i class="fa fa-ban"></i>
										Reject
								</button>
							</div>`;
				},
			},
		],
	});
	return dataTable;
}
$(document).ready(function () {
	if (localStorage.getItem("accessToken")) {
		dataTable = initDataTable();
	}
});

$("#saveEdit").click(function (e) {
	e.preventDefault();
	$.ajax({
		type: "POST",
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		url: "/api/leaves",
		headers: {
			Authorization: "Bearer " + localStorage.getItem("accessToken"),
		},
		data: JSON.stringify({
			id: $("#item_id").val(),
			startDate: $("#startDatetimepicker")
				.datetimepicker("date")
				.format("MM/DD/YYYY HH"),
			endDate: $("#endDatetimepicker")
				.datetimepicker("date")
				.format("MM/DD/YYYY HH"),
			leaveType: $("#leave-type").val(),
			reason: $("#reason").val(),
			workDissemination: $("#work-dissemination").val(),
			contactDetails: $("#contact-details").val(),
			status: "UPDATED",
		}),
		success: function (res) {
			console.log(res);
			dataTable.ajax.reload();
			$("#editModal").modal("hide");
		},
	});
});
