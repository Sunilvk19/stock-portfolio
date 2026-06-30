package simulation

import (
	"log"
	"market-data-service/config"
	"market-data-service/models"
	"math"
	"math/rand"
	"time"
)

func StartPriceSimulation() {

	// Run every second
	ticker := time.NewTicker(1 * time.Second)
	defer ticker.Stop()

	// Random number generator
	random := rand.New(rand.NewSource(time.Now().UnixNano()))

	log.Println("Background Price Simulation Started Successfully")

	for range ticker.C {

		var stocks []models.Stock

		// Fetch all active stocks
		err := config.DB.Where("is_active = ?", true).Find(&stocks).Error
		if err != nil {
			log.Println("Failed to fetch stocks:", err)
			continue
		}

		if len(stocks) == 0 {
			log.Println("No active stocks found")
			continue
		}

		for _, stock := range stocks {

			oldPrice := stock.CurrentPrice

			// Generate random change between -2% and +2%
			changePercent := (random.Float64()*4 - 2) / 100

			newPrice := oldPrice + (oldPrice * changePercent)

			// Never allow price below ₹1
			if newPrice < 1 {
				newPrice = 1
			}

			// Round to 2 decimal places
			newPrice = math.Round(newPrice*100) / 100

			err := config.DB.Model(&models.Stock{}).
				Where("symbol = ?", stock.Symbol).
				Updates(map[string]interface{}{
					"current_price":  newPrice,
					"previous_close": oldPrice,
				}).Error

			if err != nil {
				log.Printf("Failed to update %s : %v\n", stock.Symbol, err)
				continue
			}

			log.Printf(
				"%s | Old Price: %.2f | New Price: %.2f",
				stock.Symbol,
				oldPrice,
				newPrice,
			)
		}
	}
}