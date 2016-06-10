    $(document).ready(
        $("#processingPathForm").validate({
            rules: {
                "nextControllerUnit.id":{
                    required : true
                },
                "taskType.id": {
                    required : true
                }
            },messages:{
                "nextControllerUnit.id" : "Please select the next Controller Unit",
                "taskType.id": "Please select the Task Type"
            },
            highlight: function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight: function(element) {
                $(element).closest('.form-group').removeClass('has-error');
            },
            errorClass: 'help-block'
        })
    );