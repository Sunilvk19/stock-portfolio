package config

type Config struct {
	DBHost     string
	DBPort     string
	DBUser     string
	DBPassword string
	DBName     string
	ServerPort string
}

// func (c *Config) ConnectDB(config *Config) {
// 	panic("unimplemented")
// }

func Load() *Config {
	config := &Config{
		DBHost:     "localhost",
		DBPort:     "3306",
		DBUser:     "root",
		DBPassword: "coder",
		DBName:     "StockPortfolioManagement",
		ServerPort: "8081",
	}
	return config
}
