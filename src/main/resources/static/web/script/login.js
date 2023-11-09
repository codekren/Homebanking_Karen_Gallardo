const { createApp } = Vue

  createApp({
    data() {
      return {
        email:"",
        password:"",
        infoLogin:"",
        showLogin: true,
        name: "",
        lastName:"",
        infoRegister:"",
        errorEmail:false,
      }
    },
    created(){  
    },
    methods:{
        login() {
            this.errorEmail = !this.validateEmail(this.email);
            this.infoLogin = `email=${this.email}&password=${this.password}`;

            axios.post('/api/login', this.infoLogin)
              .then(response => {
                console.log("Successful request", response.data);
                Swal.fire({
                  position: 'center',
                  icon: 'success',
                  title: 'Welcome',
                  showConfirmButton: false,
                  timer: 2000
                })
                setTimeout(()=> {
                  location.href = '/web/pages/accounts.html';
                },3000);

              })
              .catch(err => {
                console.log(err);
                if (this.email = "" || this.password === "") {
                  Swal.fire({
                      icon: "error",
                      title: "Error...",
                      text: "Fill in all fields",
                      color: "#fff",
                      background: "#1c2754",
                      confirmButtonColor: "#17acc9",
                  });
              } else {
                  Swal.fire({
                      icon: "error",
                      title: "Invalid user",
                      text: "This user is not registered",
                      color: "#fff",
                      background: "#1c2754",
                      confirmButtonColor: "#17acc9"
                  });
                }
                
               
            })
            .finally(() => {
                this.email = "";
                this.password = "";
            });
        },
        validateEmail(email){
          const mailOk = /\S+@\S+\.\S+/
          return mailOk.test(email) 

        },
        
        toggleView() {
          this.showLogin = !this.showLogin;
        },
        register(){
          this.errorEmail = !this.validateEmail(this.email);
          this.infoRegister = `name=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`;
          console.log(this.infoRegister);
          axios.post('/api/clients',this.infoRegister)
          .then(response => {
           
            console.log('registered')
            
            Swal.fire({
              position: 'center',
              icon: 'success',
              title: 'Your register was successful',
              showConfirmButton: false,
              timer:1800
            })
            console.log(this.login());
        

          })
          .catch(err => {
            console.log(err)
            if (this.email = "" || this.password === "") {
              Swal.fire({
                  icon: "error",
                  title: "Error...",
                  text: "Fill in all fields",
                  color: "#fff",
                  background: "#1c2754",
                  confirmButtonColor: "#17acc9",
              });
          } else {
              Swal.fire({
                  icon: "error",
                  title: "Invalid user",
                  text: "This user is not registered",
                  color: "#fff",
                  background: "#1c2754",
                  confirmButtonColor: "#17acc9"
              });
            }
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