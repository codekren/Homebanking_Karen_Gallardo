const { createApp } = Vue

createApp({
  data() {
    return {
      dataAccount:{},
      parametro:null,
      idAccount:0,
      transaction:{},

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
        console.log('signed out!!!')
        window.location.href = '/web/index.html';
      })
      .catch(err=>console.log("error"))
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
  }
}).mount('#app')