    $(document).ready(
            $("#distributionForm").validate({
            rules: {
                type: {
                    required : true
                },
                parameterA: {
                    required : true,
                    min: 1
                },
                parameterB: {
                    required : true,
                    min: 1
                }
            },messages: {
                type: "Please select the type",
                parameterA: {
                    required: "Please specify the parameter A",
                    min : jQuery.validator.format("The parameter A must be greater or equal than {0}")
                },
                parameterB: {
                    required: "Please specify the parameter B",
                    min : jQuery.validator.format("The parameter A must be greater or equal than {0}")
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
