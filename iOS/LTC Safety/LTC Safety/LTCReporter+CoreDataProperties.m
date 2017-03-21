//
//  LTCReporter+CoreDataProperties.m
//  
//
//  Created by Daniel Morris on 2017-03-20.
//
//

#import "LTCReporter+CoreDataProperties.h"

@implementation LTCReporter (CoreDataProperties)

+ (NSFetchRequest<LTCReporter *> *)fetchRequest {
	return [[NSFetchRequest alloc] initWithEntityName:@"LTCReporter"];
}

@dynamic email;
@dynamic name;
@dynamic phoneNumber;
@dynamic report;

@end
