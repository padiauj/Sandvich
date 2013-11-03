void setup() {
  Serial.begin(9600);
}


/*
Example:
014781

Where: 
1=Wheat bread
2=White bread
3=Rye bread
4=Roast Beef
5=Veggie Burger
6=Turkey
7=Lettuce
8=Tomato
*/
void serveIngredient(int c) {
  switch (c) {
    case 1:
    Serial.println("Serving: Wheat bread");
    break;
    case 2:
    Serial.println("Serving: White bread");
    break;
    case 3:
    Serial.println("Serving: Rye bread");
    break;
    case 4:
    Serial.println("Serving: Roast beef");
    break;
    case 5:
    Serial.println("Serving: Veggie burger");
    break;
    case 6:
    Serial.println("Serving: Turkey");
    break;
    case 7:
    Serial.println("Serving: Lettuce");
    break;
    case 8:
    Serial.println("Serving: Tomato");
    break;
  }
  
}

void loop() {
  if (Serial.available()) {
    if ((Serial.read()-'0') == 0) {
      Serial.println("BUSY");
      Serial.println("Serving Order...");
    while (Serial.available()) {
      serveIngredient(Serial.read()-'0');
 
     if (!Serial.available()) {
          Serial.println();
     }
    }  
    
    }
    else {
      
    }
      
  }
 delay(500); 
}
