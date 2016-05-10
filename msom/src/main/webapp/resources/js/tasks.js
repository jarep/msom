    $(document).ready(
        $("#taskForm").validate({
            rules: {
                name:{
                    required : true
                },
                efficiency:{
                    required : true,
                    min: 1
                }
            },messages:{
                name : "Please specify name of task"
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