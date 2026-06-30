package main

import (
	"fmt"
	"log"
	"market-data-service/config"
	"market-data-service/handlers"
	"market-data-service/models"
	"market-data-service/simulation"
	"github.com/gin-gonic/gin"
)

func main(){
   cfg := config.Load()
   fmt.Println("Starting Market Service")
   db := config.ConnectDB(cfg)
   err := db.AutoMigrate(&models.Stock{})
   if err != nil {
      log.Printf("Database automigrate failed: %v",err)
   }
   fmt.Println("A Database automigrate successfully")
   go simulation.StartPriceSimulation()
   router := gin.Default()
   	router.GET("/api/stocks", handlers.GetStockHandler)
	router.GET("/api/stocks/:symbol",handlers.GetStockBySymbol)
	router.GET("/api/stocks/all",handlers.GetAllStocksHandler)
   log.Printf("Market Data REST Service listening on port %s...", cfg.ServerPort)
	if err := router.Run(":" + cfg.ServerPort); err != nil {
	   log.Fatal("Failed to start the web server engine: ", err)
	}
}