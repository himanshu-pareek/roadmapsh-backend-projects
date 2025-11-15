rootProject.name = "image-processing-service"

// Core Entities
include("core")

// Restful APIs
include("api")

// Job Processor
include("processor")
// * Image processor implementations
include("opencv")

// Database implementation
include("mongodb")

// Messaging implementation
include("amqp:common")
include("amqp:producer")
include("amqp:consumer")

// Storage implementation
include("filesystem")
