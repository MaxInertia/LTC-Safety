//
//  LTCConcernStatus+CoreDataClass.m
//  
//
//  Created by Allan Kerr on 2017-02-01.
//
//

#import "LTCConcernStatus+CoreDataClass.h"

@implementation LTCConcernStatus

+ (instancetype)statusWithData:(nonnull GTLRClient_ConcernStatus *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context {
    
    NSAssert(data != nil, @"The status data must be non-null");
    NSAssert(context != nil, @"The managed object context must be non-null");
    
    NSEntityDescription *description = [NSEntityDescription entityForName:@"LTCConcernStatus" inManagedObjectContext:context];
    return [[LTCConcernStatus alloc] initWithData:data entity:description insertIntoManagedObjectContext:context];
}

/**
 Constructs the concern status given data passed through the statusWithData method.

 @param data    The datastore concern status data that will be passed to create the local concern status.
 @param entity  The entity description for this object.
 @param context The context object space this concern status will be stored in.

 @return The constructed LTCConcernStatus
 */
- (instancetype)initWithData:(nonnull GTLRClient_ConcernStatus *)data entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
        self.creationDate = data.creationDate.date;
        self.concernType = data.type;
    }
    return self;
}

@end
