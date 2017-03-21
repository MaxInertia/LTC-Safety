//
//  LTCConcernStatus+CoreDataProperties.m
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCConcernStatus+CoreDataProperties.h"

@implementation LTCConcernStatus (CoreDataProperties)

+ (NSFetchRequest<LTCConcernStatus *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCConcernStatus"];
}

@dynamic concernType;
@dynamic creationDate;
@dynamic status;

@end
