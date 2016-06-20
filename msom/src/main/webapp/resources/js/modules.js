    $(document).ready(
            $("#moduleForm").validate({
            rules: {
                name:{
                    required : true
                },
                cores:{
                    required : true,
                    min:1,
                    digits: true
                },
                efficiency:{
                    required : true,
                    min: 1,
                    digits: true
                },
                "controllerUnit.id": {
                    required : true
                }
            },messages:{
                name : "Please specify the name",
                cores: {
                    required : "Please specify the number of cores",
                    min : jQuery.validator.format("The module must have at least {0} core")
                },
                efficiency: {
                    required : "Please specify the efficiency",
                    min : jQuery.validator.format("The efficiency must be greater than {0}")
                },
                "controllerUnit.id" : "Please select the Controller Unit"
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
