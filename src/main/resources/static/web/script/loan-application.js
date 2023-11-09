const { createApp } = Vue

createApp({
  data() {
    return {
        typeLoan : null,
        dataLoans:[],
        payments:0,
        listPayments:[],
        amount:0,
        clientAccounts:[],
        accountSelected:"",
        maxAmount:0,
        selectedLoan:[],
        monthlyPayment:0,
        listPercentage:[],
        percentage:0,
        
    }
  },
  created(){
    this.loans()    
    this.accountsDestination()

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
    loans(){
        axios('/api/loans')
        .then(response => {
            this.dataLoans = response.data
            
          
        })
        .catch(error => {
            console.log (error)
        })
        
  },
    updateListPayments(){
            this.selectedLoan = this.dataLoans.find(loan => loan.id == this.typeLoan);
            console.log(this.selectedLoan);
            this.maxAmount = this.selectedLoan.maxAmount;
            this.listPayments = this.selectedLoan ? this.selectedLoan.payments : [];

            const interestRate = 1.20;

            if (this.amount > 0 && this.payments > 0 && this.typeLoan !== null) {
                
                this.monthlyPayment = ((this.amount * interestRate) /(this.payments));
                console.log(this.monthlyPayment);
            } else {
                this.monthlyPayment = 0;
            }
        },

    accountsDestination(){
        axios('/api/clients/current/accounts')
        .then(datos=> {
          this.clientAccounts=datos.data
  
        })
        .catch(err=> console.log('error'))
    },

    loanCreated() {
        Swal.fire({
          title: 'Are you sure to apply?',
          showDenyButton: true,
          confirmButtonText: 'Sure',
          denyButtonText: 'Im not sure',
        }).then((result) => {
          if (result.isConfirmed) {
            const infoCreate = {
              loanId: this.typeLoan,
              amount: this.amount,
              payments: this.payments,
              percentage: this.percentage,
              accountDestination: this.accountSelected,
            };

            axios
              .post('/api/loans', infoCreate)
              .then(() => {
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Your loan is done',
                    showConfirmButton: false,
                    timer: 1500

                  })
                  setTimeout(()=> {
                    this.typeLoan = null;
                    this.amount = 0;
                    this.payments = 0;
                    this.percentage = 0;
                    this.accountDestination = '';
                    window.location.href = '/web/pages/accounts.html';
                  },3000);
                })
                
              .catch((err) => {
                console.log(err);
                Swal.fire({
                  position: 'center',
                  icon: 'error',
                  title: 'The amount must be greater than 0',
                  showConfirmButton: false,
                  timer: 1800

                })
              });
            
          } else if (result.isDenied) {
            Swal.fire('The request was not made.', '', 'info');
            this.typeLoan = null;
            this.amount = 0;
            this.payments = 0;
            this.accountDestination = '';
          }
        },
)}
}
      
    
}).mount('#app')