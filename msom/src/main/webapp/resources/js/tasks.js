    $(document).ready(
        $("#taskForm").validate({
            rules: {
                name:{
                    required : true
                },
                "taskType.id": {
                    required : true
                }
            },messages:{
                name : "Please specify the name",
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