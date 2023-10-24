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
                  title: 'Wellcome',
                  showConfirmButton: false,
                  timer: 1500
                })
                setTimeout(()=> {
                  window.location.href = '/web/pages/accounts.html';
                },3000);
                

              })
              .catch(err => {
                console.log(err);
                Swal.fire({
                  icon: 'error',
                  title: 'Oops...',
                  text: 'Something went wrong!',
                  
                })
            })
            .finally(() => {
                this.email = "";
                this.password = "";
            });
        },
        
        register(){

          this.infoRegister = `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`;

          axios.post('/api/clients',this.infoRegister)
          .then(async () => {
            await Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Register successful',
              showConfirmButton: false,
              timer: 1500
            })
           
            console.log('registered')
            console.log(this.login())            
          })
          .catch(err => {
            console.log(err)
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
             
            })
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