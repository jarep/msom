    $(document).ready(
            $("#distributionForm").validate({
            rules: {
                type: {
                    required : true
                },
                parameterA: {
                    required : true,
                    min: 0
                },
                parameterB: {
                    required : true,
                    min: 0
                }
            },messages: {
                type: "Please select the type",
                parameterA: {
                    required: "Please specify the parameter A",
                    min : jQuery.validator.format("The parameter A must be a non-negative number")
                },
                parameterB: {
                    required: "Please specify the parameter B",
                    min : jQuery.validator.format("The parameter A must be a non-negative number")
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
