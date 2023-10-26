const { createApp } = Vue

  createApp({
    data() {
      return {
        email:"",
        password:"",
        infoLogin:"",
        errorMessage:"",
        customErrorMessage: "",
        showLogin: true,
        name: "",
        lastName:"",
        infoRegister:""
      }
    },
    created(){  
    },
    methods:{
        login() {
            this.infoLogin = `email=${this.email}&password=${this.password}`;
    
            axios.post('/api/login', this.infoLogin)
              .then(response => {
                console.log("Successful request", response.data);
                Swal.fire({
                  position: 'top-end',
                  icon: 'success',
                  title: 'Welcome',
                  showConfirmButton: false,
                  timer: 1500
                })
                setTimeout(()=> {
                  window.location.href = '/web/pages/accounts.html';
                },3000);


              })
              .catch(err => {
                console.log(err);
                this.errorMessage = "Invalid credentials. Please check your username and password and try again.";
                setTimeout(() => {
                    this.errorMessage = "";
                  }, 5000);
            })
            .finally(() => {
                this.email = "";
                this.password = "";
            });
        },
        
        toggleView() {
          this.showLogin = !this.showLogin;
        },
        register(){

          this.infoRegister = `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`;

          axios.post('/api/clients',this.infoRegister)
          .then(response => {
            this.errorMessage = "Successful register"
            console.log('registered')
            setTimeout(() => {
              window.location.href='/web/pages/accounts.html';
            }, 3000);
          })
          .catch(err => {
            console.log(err)
            this.errorMessage = "Registration failed. Please check the provided information and try again.";
              setTimeout(() => {
                  this.errorMessage = "";
                }, 5000);
          })
          .finally(() => {
            this.email = "";
            this.password = "";
            this.name = "";
            this.lastName = "";
          });
        },
        
    }
  }).mount('#app')