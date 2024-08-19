$(document).ready(function() {
    // Initialize Kendo UI Grid
    $("#grid").kendoGrid({
        dataSource: {
            transport: {
                read: {
                    url: "/link/data", // Your endpoint URL to fetch data
                    dataType: "json"
                }
            },
            schema: {
                model: {
                    fields: {
                        id: { type: "number" },
                        status: { type: "string" },
                        url: { type: "string" },
                        time: { type: "string" }
                    }
                }
            },
            pageSize: 10 // Number of records per page
        },
        height: 550, // Grid height
        scrollable: true, // Enable vertical scrolling
        sortable: true, // Enable sorting
        pageable: true, // Enable paging
        columns: [
            { field: "id", title: "ID" },
            { field: "status", title: "Title" },
            { field: "url", title: "URL" },
            { field: "time", title: "time" }
        ]
    });
});
