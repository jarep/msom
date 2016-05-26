    $(document).ready(
        $("#taskTypeForm").validate({
            rules: {
                name:{
                    required : true
                },
                difficulty: {
                    required : true,
                    min: 1
                }
            },messages:{
                name : "Please specify name of task type",
                difficulty: {
                    required : "Please specify difficulty of task type",
                    min : jQuery.validator.format("Needs to be at least difficulty  {0}.")
                }
            },
            highlight: function(element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight: function(element) {
                $(element).closest('.form-group').removeClass('has-error');
            },
            errorClass: 'help-block',
        })
    );