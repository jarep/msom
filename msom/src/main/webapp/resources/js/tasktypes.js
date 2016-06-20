    $(document).ready(
        $("#taskTypeForm").validate({
            rules: {
                name:{
                    required : true
                },
                difficulty: {
                    required : true,
                    min: 1,
                    digits: true
                }
            },messages:{
                name : "Please specify the name",
                difficulty: {
                    required : "Please specify the difficulty",
                    min : jQuery.validator.format("The difficulty must be greater than {0}")
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