const { createApp } = Vue

createApp({
  data() {
    return {
      dataAccount:{},
      parametro:null,
      idAccount:0,
      transaction:{},
      filterAccount:{},
      

    }
  },
  created(){
    
    this.parametro=location.search;
    let params= new URLSearchParams (this.parametro) ;
    this.idAccount =params.get("id") ;
    this.loadData();
    
    

  },

  methods:{
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

     formatDateTime(dateTime) {
      const options = {  month: 'long', day: 'numeric',year: 'numeric', hour: '2-digit', minute: '2-digit' };
      return new Date(dateTime).toLocaleDateString('en-US', options);
    },  
    
    loadData(){
      axios('http://localhost:8080/api/accounts/'+this.idAccount)
      .then(datos=> {
        this.dataAccount=datos.data
        this.transaction=this.dataAccount.transactions.sort((transaction1,transaction2)=> {
          return transaction2.id - transaction1.id
        })

        
      })
      .catch(err=> console.log('error'))
  },
  deleteAccount(){
    axios.put('/api/clients/current/accounts/delete',this.idAccount)
    .then(response=>{
      console.log("deleted")
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Your has delete account!',
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
}).mount('#app')