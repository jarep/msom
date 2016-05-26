    $(document).ready(
            $("#moduleForm").validate({
            rules: {
                name:{
                    required : true
                },
                cores:{
                    required : true,
                    min:1
                },
                efficiency:{
                    required : true,
                    min: 1
                }
            },messages:{
                name : "Please specify module name",
                cores: {
                    required : "Please specify number of cores",
                    min : jQuery.validator.format("Needs to have at least {0} core.")
                },
                efficiency: {
                    required : "Please specify efficiency",
                    min : jQuery.validator.format("Efficiency must be higher then {0}")
                }
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
