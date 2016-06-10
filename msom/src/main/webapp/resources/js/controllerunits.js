    $(document).ready(
            $("#controllerUnitForm").validate({
            rules: {
                name: {
                    required : true
                },
                "model.id": {
                    required : true
                }
            },messages: {
                name: "Please specify the name",
                "model.id": "Please select the Model"
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
