const { createApp } = Vue

createApp({
  data() {
    return {
      dataClient:{},
      dataLoans:[],      
      idAccount:0,
      filterActive:{},
      accounts:{},
      
      
    }
  },
  created(){
    this.loadData();    
    
    
  },

  methods:{
    createLoan(){
     
        window.location.href = '/web/pages/loan-application.html';

    },

    logOut(){
      axios.post('/api/logout')
      .then(response => {
        console.log('signed out!!!');
        Swal.fire({
          position: 'center', 
          icon: 'warning',
          title: 'Your session has been closed',
          showConfirmButton: false,
          timer: 2000
        })
        setTimeout(()=> {
          window.location.href = '/web/index.html';
        },2000);
        

      })
      .catch(err=>console.log("error"))
     },

    loadData(){
      axios('http://localhost:8080/api/clients/current')
      .then(datos=> {
        this.dataClient=datos.data
        this.accounts= this.dataClient.accounts   
        this.dataLoans=this.dataClient.loans.sort((loan1,loan2)=>{
          return loan2.id - loan1.id
        })
      
      })

      .catch(err=> console.log('error'))
  },
  createAccount(){
    Swal.fire({
      title: 'Select Account Type',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'SAVINGS',
      cancelButtonText: 'CHECKING',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.createTypeAccount("SAVINGS");
      } else {
        this.createTypeAccount("CHECKING");
      }
    });
  },
  
  createTypeAccount(typeAccount){
    console.log(typeAccount)
    let type = `type=${typeAccount}`
      axios.post('/api/clients/current/accounts', type)
      .then(response=>{
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Well done, you have a new account!',
          showConfirmButton: false,
          timer: 1500
        })
        setTimeout(()=> {
          location.reload();
        },1500);
      
        
      })
      .catch(err=> console.log("err"))

    },
    
    

    }
  
  }
).mount('#app')