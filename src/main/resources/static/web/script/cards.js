const { createApp } = Vue

createApp({
  data() {
    return {
      dataClient:{},
      cardDebits:[],
      cardCredits:[],
      currentDate: new Date(),
     
      
    }
  },
  created(){
    this.loadData();
    
    

  },

  methods:{
    loadData(){
      axios('http://localhost:8080/api/clients/current')
      .then(datos=> {
        this.dataClient=datos.data 
        this.cardDebits= this.dataClient.cards.filter(card=> card.type=="DEBIT")
        this.cardCredits= this.dataClient.cards.filter(card=> card.type=="CREDIT")

      

      })
      .catch(err=> console.log('error'))
  },
  formatDate(date) {
    return new Date(date).toLocaleString('en-US', { month: '2-digit', year: '2-digit' });
    },
  
  toCreateCard(){
    
      location.href = "./create-cards.html";

  },
  deleteCard(id){
    axios.patch('/api/clients/current/cards/delete',`id=${id}`)
    .then(response=>{
      console.log("deleted")
      Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Your has delete card!',
        showConfirmButton: false,
        timer: 1500
      })
      setTimeout(()=> {
        location.reload();
      },1500);
    
      
    })
    .catch(err=> console.log("err"))
    Swal.fire({
      icon: "error",
      title: "Error...",
      text: "upss something wrong happens",
      color: "#fff",
      background: "#1c2754",
      confirmButtonColor: "#17acc9",
  });

  },

  logOut(){
      axios.post('/api/logout')
      .then(response => {
        console.log('signed out!!!')
        Swal.fire({
          title: 'Signed Out',
          showClass: {
            popup: 'animate__animated animate__fadeInDown'
          },
          hideClass: {
            popup: 'animate__animated animate__fadeOutUp'
          }
        })
        window.location.pathname = '/web/index.html';
      })
      .catch(err=>console.log("error"))
     },
    
    
  }
}).mount('#app')