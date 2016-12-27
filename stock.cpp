using namespace std;
#include<iostream>

void stockpredict(int a[],int n)
{
int maxsofar=0;
int sell=0;
int buy=0;
int cursell=0;
int profit=0;
int finalprofit=-1;
maxsofar=a[n-1];
sell=-1;
buy=-1;
cursell=-1;
for(int i=n-2;i>=0;i--)
{
	//cout<<a[i]<<" "<<maxsofar<<endl;
	if(a[i]>maxsofar)
	{
		maxsofar=a[i];
		cursell=i;
		//cout<<a[i]<<" "<<maxsofar<<endl;
			
	}
	else
	{
		profit=maxsofar-a[i];
		if(profit>finalprofit)
		{
		//cout<<"here";		
		finalprofit=profit;
		buy=i;
		sell=cursell;
		}		
		
		//cout<<"p"<<profit<<endl;
	}

	
		

	//cout<<"p "<<finalprofit<<" "<<maxsofar<<" "<<profit<<endl;
		
}
	cout<<finalprofit<<" "<<buy<<" "<<sell<<endl;

}


int main()
{
int n;
cin>>n;
int stockvalues[n];

for(int i=0;i<n;i++){
	cin>>stockvalues[i];
}
stockpredict(stockvalues,n);
}
