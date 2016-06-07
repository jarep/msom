    $(document).ready(
            $("#controllerUnitForm").validate({
            rules: {
                name: {
                    required : true
                }
            },messages: {
                name: "Please specify the name"
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
