//
//  LTCNewConcernViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-26.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XLForm/XLForm.h>
#import "LTCConcern+CoreDataClass.h"

@interface LTCNewConcernViewModel : XLFormDescriptor
@property (readonly, nonatomic, weak) LTCConcern *concern;
- (instancetype)initWithContext:(NSManagedObjectContext *)context;
@end
