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
        
    }
  },
  created(){
    this.loans()    
    this.accountsDestination()

  },

  methods:{
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
              accountDestination: this.accountSelected,
            };

            axios
              .post('/api/loans', infoCreate)
              .then(() => {
                Swal.fire('The request was made!', '', 'success');
                this.typeLoan = null;
                this.amount = 0;
                this.payments = 0;
                this.accountDestination = '';                
                location.href = '/web/pages/accounts.html';
              })
              .catch((err) => {
                console.log(err);
              });
          } else if (result.isDenied) {
            Swal.fire('The request was not made.', '', 'info');
            this.typeLoan = null;
            this.amount = 0;
            this.payments = 0;
            this.accountDestination = '';
          }
        });
      },
    },
}).mount('#app')