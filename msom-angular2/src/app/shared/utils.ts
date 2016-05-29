import { Task } from './contracts'

export var tasksWaitingTimeAvg = (tasks : Task[]) => 
    (tasks.map(t => t.totalWaitingTime).reduce((x, y) => x + y, 0) / tasks.length).toFixed(2);

export var tasksProcessedAvg = (tasks : Task[]) => 
    (tasks.map(t => t.processed).reduce((x, y) => x + y, 0) / tasks.length).toFixed(2);

export var processingTimeAvg = (tasks : Task[]) => 
    (tasks.map(t => t.totalProcessingTime).reduce((x, y) => x + y, 0) / tasks.length).toFixed(2);