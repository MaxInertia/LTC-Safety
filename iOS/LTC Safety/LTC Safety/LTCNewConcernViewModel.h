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
@property (nonatomic, assign) SEL submissionCallback;
- (instancetype)initWithContext:(NSManagedObjectContext *)context;
- (void)submitConcernData:(void (^)(LTCConcern *concern, NSError *error))completion;
@end
