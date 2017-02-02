//
//  LTCLocation+CoreDataClass.h
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>
#import "GTLRClient.h"

@class LTCConcern;

NS_ASSUME_NONNULL_BEGIN

@interface LTCLocation : NSManagedObject
+ (instancetype)locationWithData:(nonnull GTLRClient_Location *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context;
@end

NS_ASSUME_NONNULL_END

#import "LTCLocation+CoreDataProperties.h"
