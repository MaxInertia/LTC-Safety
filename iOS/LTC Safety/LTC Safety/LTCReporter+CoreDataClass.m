//
//  LTCReporter+CoreDataClass.m
//  
//
//  Created by Allan Kerr on 2017-01-27.
//
//

#import "LTCReporter+CoreDataClass.h"
#import "LTCConcern+CoreDataClass.h"
@implementation LTCReporter

+ (instancetype)reporterWithData:(nonnull GTLRClient_Reporter *)data inManagedObjectContext:(nonnull NSManagedObjectContext *)context {
    
    NSAssert(data != nil, @"The reporter data must be non-null");
    NSAssert(context != nil, @"The managed object context must be non-null");
    
    NSEntityDescription *description = [NSEntityDescription entityForName:@"LTCReporter" inManagedObjectContext:context];
    return [[LTCReporter alloc] initWithData:data entity:description insertIntoManagedObjectContext:context];
}
/**
 Constructs the concern location given data passed through the locationWithData method.
 
 @param data    The datastore concern reporter data that will be passed to create the local concern reporter.
 @param entity  The entity description for this object.
 @param context The context object space this concern location will be stored in.
 
 @return The constructed LTCReporter
 */
- (instancetype)initWithData:(nonnull GTLRClient_Reporter *)data entity:(NSEntityDescription *)entity insertIntoManagedObjectContext:(NSManagedObjectContext *)context {
    if (self = [super initWithEntity:entity insertIntoManagedObjectContext:context]) {
        self.name = data.name;
        self.phoneNumber = data.phoneNumber;
        self.email = data.email;
    }
    return self;
}

@end
