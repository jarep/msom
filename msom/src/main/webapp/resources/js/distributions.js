    $(document).ready(
            $("#distributionForm").validate({
            rules: {
                type: {
                    required : true
                },
                parameterA: {
                    required : true
                },
                parameterB: {
                    required : true
                }
            },messages: {
                type: "Please select the type",
                parameterA: "Please specify the parameterA",
                parameterB: "Please specify the parameterB"
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
