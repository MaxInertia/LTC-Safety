//
//  LTCConcernStatus+CoreDataProperties.m
//  
//
//  Created by Allan Kerr on 2017-02-01.
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
