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

- (instancetype)initWithData:(nonnull GTLRClient_ConcernStatus *)data entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
        self.creationDate = data.creationDate.date;
        self.concernType = data.type;
    }
    return self;
}

@end
