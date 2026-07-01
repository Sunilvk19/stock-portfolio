package simulation

import (
	"context"
	"log"
	"market-data-service/config"
	"market-data-service/models"
	"math"
	"math/rand"
	"time"
	"fmt"
	"gorm.io/gorm"
)
const(
	simulationInterval = 1 * time.Second
	maxVolatilityPct = 0.02 
	maxVolumeDelta = 1000
	minPrice = 0.1
)

func StartPriceSimulation(ctx context.Context) {
	ticker := time.NewTicker(simulationInterval)
	defer ticker.Stop()
 
	random := rand.New(rand.NewSource(time.Now().UnixNano()))
	log.Println("Background price simulation started successfully")
 
	for {
		select {
		case <-ctx.Done():
			log.Println("Background price simulation stopped:", ctx.Err())
			return
		case <-ticker.C:
			if err := simulateTick(random); err != nil {
				log.Println("Simulation tick failed:", err)
			}
		}
	}
}

func simulateTick(random *rand.Rand) error {
	var stocks []models.Stock
	if err := config.DB.
		Select("symbol", "current_price", "volume").
		Where("is_active = ?", true).
		Find(&stocks).Error; err != nil {
		return fmt.Errorf("fetch active stocks: %w", err)
	}
 
	if len(stocks) == 0 {
		log.Println("No active stocks found")
		return nil
	}
 
	return config.DB.Transaction(func(tx *gorm.DB) error {
		for _, stock := range stocks {
			newPrice, newVolume := nextPriceAndVolume(stock, random)
 
			if err := tx.Model(&models.Stock{}).
				Where("symbol = ?", stock.Symbol).
				Updates(map[string]interface{}{
					"current_price":  newPrice,
					"previous_close": stock.CurrentPrice, // NOTE: see caveat below
					"volume":         newVolume,
				}).Error; err != nil {
				return fmt.Errorf("update %s: %w", stock.Symbol, err)
			}
 
			log.Printf("%s | Old Price: %.2f | New Price: %.2f | Volume: %d",
				stock.Symbol, stock.CurrentPrice, newPrice, newVolume)
		}
		return nil
	})
}

func nextPriceAndVolume(stock models.Stock, random *rand.Rand) (float64, int64) {
	changePercent := (random.Float64()*2 - 1) * maxVolatilityPct
	newPrice := stock.CurrentPrice * (1 + changePercent)
	if newPrice < minPrice {
		newPrice = minPrice
	}
	newPrice = math.Round(newPrice*100) / 100
 
	newVolume := stock.Volume + int64(random.Int31n(maxVolumeDelta))
 
	return newPrice, newVolume
}