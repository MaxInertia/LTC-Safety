//
//  LTCValueSelectionController.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCValueSelectionController.h"
#import "LTCInputAlertController.h"
#import "LTCValueSelectionViewModel.h"
#
#import "LTCRowValue.h"

@interface LTCValueSelectionController () <LTCInputAlertControllerDelegate>
@property (nonatomic, strong) LTCValueSelectionViewModel *viewModel;
@end

@implementation LTCValueSelectionController
@dynamic fileName;

- (NSString *)fileName {
    NSAssert(NO, @"Subclasses of %@ must override - fileName", self.class);
    return nil;
}

- (instancetype)init {
    
   

    return self;
}

- (XLFormRowDescriptor *)_createRow:(LTCRowValue *)rowValue action:(SEL)action {
    
      return ni;
}

- (void)_selectCategory:(XLFormRowDescriptor *)categoryDescriptor {

}

- (void)_selectOther:(XLFormRowDescriptor *)descriptor {
    
}

- (void)alertController:(LTCInputAlertController *)controller didFinishWithInput:(NSString *)input {
    
  
}

- (void)_finishWithValue:(NSString *)value {
    
   
}

@end
